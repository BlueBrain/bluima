#!/bin/bash

##
## Create a new resource package
##
## Usage:
##  ./create_resource.sh --id <id> --file <path> --output <dir> --category <category> --name <name>
##
##  - The id should be a valid Maven artifactId.
##  - The output directory is denoted by <dir> relative to this script.
##  - The category is the end of the package; i.e. ch.epfl.bbp.nlp.res.<category>.
##  - The name should be in CamelCase.
##
## This script will create a class ch.epfl.bbp.nlp.res.<category>.<name>Resource that
## extends ch.epfl.bbp.nlp.ModelResource and loads the given file, and put that class
## in a Maven package.
##

error() {
    echo "$@" 1>&2
    exit 1
}

assert() {
    if [ $? -ne 0 ]; then error "assertion failed $@"; fi
}

idOk=false
fileOk=false
outputOk=false
categoryOk=false
nameOk=false

# read arguments
until [ -z "$1" ]
do
  if [ -z "$2" ]; then error "argument $1 need a value"; fi

  case "$1" in
    --id)
        id="$2"
        idOk=true
        ;;
    --file)
        path="$2"
        if [ -f "$path" -a -r "$path" ]
        then
            file=`basename $path`
            fileOk=true
        else
            error "$path is not a file or is not readable"
        fi
        ;;
    --output)
        output="$2"
        if [ -e "$output" ]; then error "output directory $output already exists"; fi
        outputOk=true
        ;;
    --category)
        category="$2"
        categoryOk=true
        ;;
    --name)
        name="$2"
        className="${name}Resource"
        classNameTest="${className}Test"
        nameOk=true
        ;;
    *)
        error "Unknown argument $1"
        ;;
  esac

  shift # arg
  shift # val
done

if [ "$idOk" = false ]; then error "missing id"; fi
if [ "$fileOk" = false ]; then error "missing file"; fi
if [ "$outputOk" = false ]; then error "missing output directory"; fi
if [ "$categoryOk" = false ]; then error "missing category"; fi
if [ "$nameOk" = false ]; then error "missing name"; fi

# compute package
basePkg='ch.epfl.bpp.nlp.res'
pkg="$basePkg.$category"
basePkgPath=`echo $basePkg | tr . /`
pkgPath=`echo $pkg | tr . /`


# create file hierarchy
BASE=`dirname $0`

mainPath="$BASE/$output/src/main/java/$pkgPath"
mkdir -p "$mainPath"
assert "create base folders"
resPath="$BASE/$output/src/main/resources"
mkdir -p "$resPath"
assert "create base folders"
testPath="$BASE/$output/src/test/java/$basePkgPath"
mkdir -p "$testPath"
assert "create base folders"

cp "$path" "$resPath/$file"
assert "import file"

cat >> "$testPath/$classNameTest.java" <<EOF
package $basePkg;

import org.junit.Assert;
import org.junit.Test;

import $pkg.$className;
import ch.epfl.bbp.nlp.ModelProxy;
import ch.epfl.bbp.nlp.ModelProxyException;
import ch.epfl.bbp.nlp.ModelStream;

public class $classNameTest {

    @Test
    public final void testGetModelFromName() throws ModelProxyException {
        ModelStream stream = ModelProxy
                .getStream("$pkg.$className");
        Assert.assertNotNull("Resource is not null", stream);
    }

    @Test
    public final void testGetModelFromInstance() throws ModelProxyException {
        ModelStream stream = ModelProxy.getStream(new $className());
        Assert.assertNotNull("Resource is not null", stream);
    }
}

EOF
assert "generate test class"

cat >> "$mainPath/$className.java" <<EOF
package $pkg;

import ch.epfl.bbp.nlp.ModelResource;

/**
 * $name Resource
 */
public class $className implements ModelResource {
    public String getResourcePath() {
        return "/$file";
    }
}

EOF
assert "generate resource class"

cat >> "$BASE/$output/pom.xml" <<EOF
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <parent>
    <groupId>ch.epfl.bbp.nlp</groupId>
    <artifactId>bluima_resource_parent</artifactId>
    <version>0.1</version>
    <relativePath>../pom.xml</relativePath>
  </parent>

  <artifactId>$id</artifactId>

  <name>$name Resource</name>
</project>

EOF
assert "generate pom"

echo "Success! Now add '<module>$output</module>' in the $BASE/pom.xml"

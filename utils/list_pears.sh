#!/bin/sh
#
# list_pears.sh
# $Id$
#
# Lists all components from the installed PEARs, either in a human
# format (default) or in Confluence wiki language (-wiki option).
# Searches PEARs either in $UIMA_PEARS, if set, or in ., if not.
#
# (c) EPFL, Jean-Cedric Chappelier 02/2012
#

prog=$(basename $0)

usage () {
    echo "usage: ${prog} [-h|-?] [-wiki] [-color]"
}

error () {
    echo $* 1>&2
    usage
    exit 1
}

help () {
    usage
    cat<<EOF

  -h or -? : Print this message and stops.
  -wiki    : displays results in Confluence wiki language.
  -color   : displays results with colors (if possible).
             Can be combined with -wiki.
EOF
    exit 0
}

dspl_head=''
dspl_itemi='    '
dspl_itemii='        '
dspl_itemiinxt='            '
dspl_rq=''

wiki=0
colors=0
while [ $# -ge 1 ]; do
    case "$1" in
      -h|-\?) help;;
      -wiki)
            wiki=1
            dspl_head='h1. '
            dspl_itemi='* '
            dspl_itemii='** '
            dspl_itemiinxt='   '
            dspl_rq='??'
      ;;
      -color|-colors) colors=1;;
    esac
    shift
done

if [ "$colors" = '1' ]; then
    if [ "$wiki" = '1' ]; then
        cend='{color}'
        cred='{color:red}'
        cblue='{color:blue}'
        cgreen='{color:green}'
    else
        esc='\033['
        cend="${esc}0m"
        cred="${esc}31m"
        cblue="${esc}36m"
        cgreen="${esc}32m"
    fi
else
    cend=''
    cred=''
    cblue=''
    cgreen=''
fi

where="${UIMA_PEARS:-.}"

# ----------------------------------------------------------------------
xquery () { 
# provide filename as 1st arg.

# set here your xquery command

##too sloooow    java -classpath "$SAXONPATH/saxon9he.jar" net.sf.saxon.Query  -s:"$1" -qs:'declare default element namespace "http://uima.apache.org/resourceSpecifier" ; for $x in (//analysisEngineMetaData/description|//processingResourceMetaData/description) return $x' \!method=text 2>/dev/null

## much faster alternative (requires libxml-xpath-perl):
    xpath -q -e '//analysisEngineMetaData/description/text()' "$1"
}
# ----------------------------------------------------------------------

echo "${dspl_head}PEARs installed in ${cred}${where}${cend}:"

for pear in "$where"/*; do
    if [ -d "$pear/desc" ]; then
	echo "${dspl_itemi}in ${cblue}$(basename $pear)${cend}:"
	for desc in "$pear"/desc/*.xml; do
            if [ -f "$desc" ]; then
                echo "${dspl_itemii}${cgreen}$(basename $desc)${cend}"
                if a=$(xquery "$desc"); then
                    echo "${dspl_itemiinxt}$a"
                fi
            fi
	done
    fi
done

if [ "$wiki" = '1' ]; then
    echo
fi
echo "${dspl_rq}(created by $prog, $(date))${dspl_rq}"


package ch.epfl.bbp.uima.utils;

import java.io.File;

import org.apache.uima.pear.tools.PackageInstaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wrapper around {@link PackageInstaller} to be able to use it from maven
 * 
 * @author renaud.richardet@epfl.ch
 */
public class PearInstaller {
    private static Logger LOG = LoggerFactory.getLogger(PearInstaller.class);

    public static final String USAGE = "Usage: PearInstaller path_to_install_to pear_package_file [validate]";

    public static void main(String[] args) {

	if (args.length < 2) {
	    String USAGE = "Usage: PearInstaller path_to_install_to pear_package_file [validate]";
	    System.out.println(USAGE);
	    throw new RuntimeException(USAGE);
	}

	String pearRepoStr = args[0];
	String pearFileStr = args[1];
	String validateStr = args[2];
	boolean validate = validateStr != null
		&& validateStr.equals("validate");

	File pear = new File(pearFileStr);
	if (!pear.exists()) {
	    String msg = "PEAR file does not exist: " + pearFileStr;
	    System.out.println(msg);
	    throw new RuntimeException(msg);
	}

	File pearRepo = new File(pearRepoStr);
	if (!pearRepo.exists()) {
	    String msg = "PEAR repo does not exist: " + pearRepoStr;
	    System.out.println(msg);
	    throw new RuntimeException(msg);
	}

	if (!pearRepo.isDirectory()) {
	    String msg = "PEAR repo does not exist: " + pearRepoStr;
	    System.out.println(msg);
	    throw new RuntimeException(msg);
	}

	LOG.debug("installing PEAR, source='" + pearFileStr + "', repo='"
		+ pearRepoStr + "'");

	PackageInstaller.installPackage(pearRepo, pear, validate);
    }
}

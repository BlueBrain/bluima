#! /usr/bin/perl

# Generates LaTeX table of most common terms per topics 
# Takes output of DCA's "mpupd -t [nr of tokens per topics],0 dca" as input

use warnings;
use strict;

# mpupd -t [nr of tokens per topics ],0 dca:
#Component 0 (28921.8): key(244,0.024245) encrypt(6981,0.015807) chip(2662,0.014286) govern(5380,0.012176) clipper(549,0.011069) 

sub min{
 if($_[0] < $_[1]){
    return $_[0];
 }
 else{
    return $_[1];
 }
}


my @d;
my $lines = -1;
my $topics = 0;
while(<>) {
    my @res = /([a-z0-9]*)\(([0-9]+),([0-9.]+)\)/g;
    
 #   print($#res . " ");
    my @arr = ();
    if($lines <= 0) {
	$lines = ($#res+1)/3;
    }
    for(my $i = 0; $i < $#res; $i+=3) {
	my $lp = sprintf("%.3f", log($res[$i+2]));
	push(@arr, "$res[$i] & $lp");
#	print("$res[$i]\n");
    }
    
    push(@d, @arr);
    $topics++;
}

#print "$d[0] $lines $topics\n";

my $topics_per_row = 6;
print "\\begin{tabular}{" . "|l r"x$topics_per_row .  "|}\n";
my $topic_nr = 1;
for(my $k = 0; $k < $topics; $k+=$topics_per_row) {    
    print "\\hline\n";
    #print "\\multirow{1}{*} ";
    for(my $j = 0; $j < $topics_per_row-1 && $j + $k < $topics-1; $j++) {
	print "Topic $topic_nr & &";
	$topic_nr++;
    }
    print "Topic $topic_nr &\\\\\n";
    $topic_nr++;
    print "\\hline\n";
    for(my $i = 0; $i < $lines; $i++) {
	for(my $j = 0; $j < $topics_per_row-1 && $j +$k < $topics-1; $j++) {
	    print "$d[ ($k+$j)*$lines + $i] & ";
	}
        my $last = min(($k + $topics_per_row-1)*$lines + $i,
	($topics-1)*$lines + $i);

	    print "$d[$last]\\\\\n";

	
    }
    print "\\hline\n";
}
print '\end{tabular}' . "\n";



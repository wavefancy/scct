#!/usr/bin/perl
#Author: huangxin@picb.ac.cn	version: 1.0.0	date: Fri Dec 28 13:55:10 CST 2012

use strict;
use lib "/picb/home50/huangxin/.cpan/build";
use lib "/picb/home50/huangxin/Desktop/biosoftware/PerlLib";

die "perl $0 <ms_file> <sele_pos> <hap start> <hap end>\n" if @ARGV != 4;
my ($ms_file, $sele_pos, $start, $end) = @ARGV;

($ms_file =~ /\.gz$/ ? (open (MS_FILE, "gzip -dc $ms_file | ")) : (open (MS_FILE, " $ms_file"))) || (die "Couldn't open $ms_file: $!");
#my $ancestral = 0;
#my $derived = 0;

my @fre = ();

$/ = '//';
<MS_FILE>;
while (<MS_FILE>) {
	chomp;
	my @line = split(/\n/);
	shift @line;
	shift @line;
	my $positions = shift @line;
	my @positions = split(/ /, $positions);
	shift @positions;
	my $i = 0;
	my $sele_pos_scale = 0;
	my $ancestral = 0;
	my $derived = 0;
	for my $pos (@positions) {
		if ($pos == $sele_pos) {
			$sele_pos_scale = $i;
			last;
		}
		$i++;
	}
	for (my $i = $start - 1; $i < $end; $i++) {
		my $allele = substr($line[$i], $sele_pos_scale, 1);
		if ($allele == 0) {
			$ancestral++;
		}
		else {
			$derived++;
		}
	}
	#for my $hap (@line) {
	#	my $allele = substr($hap, $sele_pos_scale, 1);
	#	if ($allele == 0) {
	#		$ancestral++;
	#	}
	#	else {
	#		$derived++;
	#	}
	#}
	my $fre = $derived / ($ancestral + $derived);
	push @fre, $fre;
	print $ancestral,"\t",$derived,"\t",$derived / ($ancestral + $derived),"\n";
}
close MS_FILE;

my $sum = 0;
for my $fre (@fre) {
	$sum += $fre;
}

print $sum / @fre, "\n";

#############################################
#################SUBROUTINES#################
#############################################

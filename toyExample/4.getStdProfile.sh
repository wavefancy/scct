
#generate parameters for normalization.
zcat un.scct.gz | java -jar StandardizeFileGenerator.V1.0.jar 2 6 0.01 >std.profile.txt

#get final standerdized SCCT score.

zcat un.scct.gz | java -jar StandardizeFromFile.V1.0.jar 2 6 std.profile.txt | gzip >std.scct.gz
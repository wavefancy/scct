
#Count the number of mutations for derived and ancestral group.
zcat selectionMs.gz | java -jar CountTwoGroupMutations.V1.1.jar 2 300 | gzip >counts.gz


#Count the number of mutations for derived and ancestral group.
zcat selectionMs.gz | java -jar CountTwoGroupMutations.V1.0.jar 2 300 | gzip >counts.gz


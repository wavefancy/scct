# Standardize results.

#mutations.f550.gz

ss=`cat para.sh`
for l in ${ss}
do
    echo "zcat ratio.id${l}.gz | java -jar -Xmx1G ~/scct/StandardizeFromFile.V1.0.jar 2 3 std.id${l}.txt | gzip >std.ratio.id${l}.gz "
done

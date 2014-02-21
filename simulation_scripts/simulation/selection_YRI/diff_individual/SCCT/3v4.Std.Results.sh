# Standardize results. Standardize results by neutral.

#mutations.f550.gz
std="../../../bestFit/SCCT/empirical/yri/Individual"

ss=`seq 30 30 210`
for l in ${ss}
do
    echo "zcat ratio.id${l}.gz | java -jar -Xmx1G ~/jars/StandardizeFromFile.V1.0.jar 2 3 ${std}/std.id${l}.txt | gzip >std.ratio.id${l}.gz "
done

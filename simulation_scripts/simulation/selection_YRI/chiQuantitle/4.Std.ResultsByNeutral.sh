# Standardize results.

#mutations.f550.gz

neu="../../bestFit/SCCT/empirical/yri/chiQuantitle/"

ss=`cat para.sh`
for l in ${ss}
do
    echo "zcat ratio.f${l}.gz | java -jar -Xmx1G ~/scct/StandardizeFromFile.V1.0.jar 2 3 ${neu}std.f${l}.txt | gzip >std.ratio.f${l}.gz"
done

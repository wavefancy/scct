# Standardize results.

std="../../../bestFit/SCCT/empirical/yri/Length/std.f300.txt"
ss="0.5 0.1 0.01"
for l in ${ss}
do
    echo "zcat ratio.${l}.gz | java -jar -Xmx1G ~/scct/StandardizeFromFile.V1.0.jar 2 3 ${std} | gzip >std.ratio.${l}.gz "
done

# Standardize results.

std="../../../bestFit/SCCT/empirical/ceu/Length/std.f300.txt"
ss=`seq 500 100 2000`
for l in ${ss}
do
    echo "zcat ratio.${l}.gz | java -jar -Xmx1G ~/scct/StandardizeFromFile.V1.0.jar 2 3 ${std} | gzip >std.ratio.${l}.gz "
done

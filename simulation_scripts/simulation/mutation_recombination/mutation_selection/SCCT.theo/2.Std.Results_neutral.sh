# Standardize results.

std="../../mutation_neu/SCCT.theo/std.id"
ss=`cat ../para.sh`
for l in ${ss}
do
    echo "zcat ratio.${l}.gz | java -jar -Xmx1G ~/scct/StandardizeFromFile.V1.0.jar 2 3 ${std}${l}.txt | gzip >std.ratio.${l}.gz "
done

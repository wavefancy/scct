# Standardize results.

std="../../../bestFit/SCCT/empirical/yri/Individual.theo/std.id"
ss=`cat para.sh`
for l in ${ss}
do
    let "h=${l}*1"
    echo "zcat ratio.${l}.gz | java -jar -Xmx1G ~/scct/StandardizeFromFile.V1.0.jar 2 3 ${std}${h}.txt | gzip >std.ratio.${l}.gz "
done

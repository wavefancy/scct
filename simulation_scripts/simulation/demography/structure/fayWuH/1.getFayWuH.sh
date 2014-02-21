# Compute tajima's D

ss=`cat ../para.sh`
for s in ${ss}
do
    echo "zcat ../yri.${s}.gz | java -Xmx2G -jar ~/scct/FayWuHNormalized_PosNear.V1.0.jar 25 0.5000000000 | gzip > t_${s}.gz"
done

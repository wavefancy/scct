# Compute tajima's D

ss="0.1 0.01 0.5"
for s in ${ss}
do
    echo "zcat ../yri.${s}.gz | java -Xmx2G -jar ~/scct/ComputeTajimaDMS_POS_Near.v1.0.jar 25 0.5000000000 | gzip > t_${s}.gz"
done

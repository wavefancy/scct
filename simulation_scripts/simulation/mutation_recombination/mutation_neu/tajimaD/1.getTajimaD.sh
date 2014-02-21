# Compute

ss=`cat ../para.sh`
for s in ${ss}
do
    echo "zcat ../yri.${s}.gz | java -jar -Xmx1G ~/scct/ComputeTajimaDMS_window.v1.0.jar 10 51 51 | gzip >t_${s}.gz"
done


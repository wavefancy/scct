# Convert data to REHH
ss="10 0 100"
for s in ${ss}
do
    #echo "mkdir -p s${s}"
    echo "mkdir -p s${s} && cd s${s} && zcat ../../yri.${s}.gz | java -jar -Xmx1G ~/scct/RemoveMAFfromMS.V1.0.jar 0.05 | java -jar -Xmx1G ~/scct/ConvertMStoREHH.V1.0.jar 3000000 s${s}"
done

#inf="../../../yri.neu.gz"

#echo "mkdir -p n.yri"
#echo "cd n.yri && zcat ${inf} | java -jar -Xmx1G ~/jars/RemoveMAFfromMS.V1.0.jar 0.05 | java -jar -Xmx5G ~/jars/ConvertMStoREHH.V1.0.jar 3000000 n.yri"

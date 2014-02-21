# Compute two group mutations for different length.

len=`seq 50 50 1000`
inf="../../../../yri.id60.gz"

for l in ${len}
do
    echo "zcat ${inf} | java -jar -Xmx1G ~/scct/RemoveMAFfromMS.V1.0.jar 0.001 | java -jar -Xmx3G ~/scct/ComputeTwoGroupMutationsMsDiscardCenterV1.0.jar 10 ${l} 0 | grep -i 'OK' |gzip >mutations.f${l}.gz"
done

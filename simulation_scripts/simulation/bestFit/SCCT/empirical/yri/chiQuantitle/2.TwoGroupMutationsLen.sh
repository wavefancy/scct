# Compute two group mutations for different chi-sequare quantitle.

len=`cat para.sh`
inf="../../../../yri.id60.gz"

for l in ${len}
do
    echo "zcat ${inf} | java -jar -Xmx1G ~/scct/RemoveMAFfromMS.V1.0.jar 0.001 | java -jar -Xmx3G ~/jars/ComputeTwoGroupMutationsFromMsAutoLDChi.V1.0.jar 10 ${l} | grep -v 'MAF' |gzip >mutations.f${l}.gz"
done

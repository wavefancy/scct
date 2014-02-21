# Compute two group mutations for different length.

ss=`seq 30 30 210`
#inf="maf.yri.s1300.gz"

for s in ${ss}
do
    echo "zcat ../yri.id${s}.gz | java -jar -Xmx1G ~/scct/RemoveMAFfromMS.V1.0.jar 0.001 | java -jar -Xmx3G ~/scct/ComputeTwoGroupMutationsMsDiscardCenterV1.0.jar 10 300 0 | grep -i -E '(OK|ZERO)' |gzip >mutations.id${s}.gz"
done

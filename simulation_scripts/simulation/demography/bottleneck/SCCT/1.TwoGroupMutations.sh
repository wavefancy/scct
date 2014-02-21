# Get variants for computing SCCT score.

ss="0.5 0.1 0.01"
for s in ${ss}
do
    echo "zcat ../yri.${s}.gz | java -jar -Xmx1G ~/scct/RemoveMAFfromMS.V1.0.jar 0.001 |java -jar -Xmx3G ~/scct/ComputeTwoGroupMutationsMsDiscardCenterV1.0.jar 15 300 0 |grep -i -E '(OK|ZERO)' | gzip >mutations.${s}.gz"
done

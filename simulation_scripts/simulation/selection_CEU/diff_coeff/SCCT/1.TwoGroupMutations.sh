# Get variants for computing SCCT score.

ss=`seq 500 100 2000`
for s in ${ss}
do
    echo "zcat ../r300_ceu.${s}.gz | java -jar -Xmx1G ~/scct/RemoveMAFfromMS.V1.0.jar 0.001 |java -jar -Xmx3G ~/scct/ComputeTwoGroupMutationsMsDiscardCenterV1.0.jar 10 300 0 |grep -i -E '(OK|ZERO)' | gzip >mutations.${s}.gz"
done

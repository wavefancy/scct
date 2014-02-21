# Compute emprical ratio from neutral alpha.

alpha="../../../bestFit/SCCT/empirical/yri/Length/e.alpha.f300.txt"
#mutations.f550.gz

ss="10 50 100"
for l in ${ss}
do
    echo "zcat mutations.${l}.gz | python ~/scct/ComputeRatioV1.1.py 2 3 4 ${alpha} |cut -f1,2,6 |gzip >ratio.${l}.gz"
done

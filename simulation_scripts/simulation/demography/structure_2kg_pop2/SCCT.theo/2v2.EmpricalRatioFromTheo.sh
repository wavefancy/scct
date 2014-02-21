# Compute emprical ratio from neutral alpha.

alpha="../../../theoretical/theoreticRatio_final_120.txt"
#mutations.f550.gz

ss=`cat para.sh`
for l in ${ss}
do
    echo "zcat ../SCCT/mutations.${l}.gz | python ~/scct/ComputeRatioV1.1.py 2 3 4 ${alpha} |cut -f1,2,6 |gzip >ratio.${l}.gz"
done

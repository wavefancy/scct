# Compute ratio by theoretical prediction.

theo="../../theoretical/theoreticRatio_final_120.txt"

ss=`seq 50 50 1000`
for l in ${ss}
do
    echo "zcat ../chooseLen/mutations.f${l}.gz | python ~/scct/ComputeRatioV1.1.py 2 3 4 ${theo} |cut -f1,2,6 |gzip >ratio.f${l}.gz"
done

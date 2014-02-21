# Compute emprical ratio

#mutations.f550.gz
alpha="../../../../../theoretical/theoreticRatio_final_"

ss=`seq 30 30 180`
for l in ${ss}
do
    let "h=${l}*2"
    echo "zcat ../Individual/mutations.id${l}.gz | python ~/scct/ComputeRatioV1.1.py 2 3 4 ${alpha}${h}.txt |cut -f1,2,6 |gzip >ratio.id${l}.gz"
done

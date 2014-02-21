# Compute emprical ratio from neutral alpha.

#alpha="../../../theoretical/theoreticRatio_final_120.txt"
alpha="../../../theoretical/theoreticRatio_final_"

ss=`cat para.sh`
for l in ${ss}
do
    let "h=${l}*2"
    echo "zcat ../SCCT/mutations.id${l}.gz | python ~/scct/ComputeRatioV1.1.py 2 3 4 ${alpha}${h}.txt |cut -f1,2,6 |gzip >ratio.${l}.gz"
done

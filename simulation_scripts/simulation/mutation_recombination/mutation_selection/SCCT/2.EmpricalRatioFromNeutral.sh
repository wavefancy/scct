# Compute emprical ratio from neutral alpha.

alpha="../../mutation_neu/SCCT/alpha.id"
#alpha.id0.8.txt
ss=`cat ../para.sh`
for l in ${ss}
do
    echo "zcat mutations.${l}.gz | python ~/scct/ComputeRatioV1.1.py 2 3 4 ${alpha}${l}.txt |cut -f1,2,6 |gzip >ratio.${l}.gz"
done

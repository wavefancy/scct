# Compute emprical ratio

#mutations.f550.gz

ss=`cat ../para.sh`
for l in ${ss}
do
    echo "zcat mutations.id${l}.gz | python ~/scct/ComputeRatioV1.1.py 2 3 4 alpha.id${l}.txt |cut -f1,2,6 |gzip >ratio.id${l}.gz"
done

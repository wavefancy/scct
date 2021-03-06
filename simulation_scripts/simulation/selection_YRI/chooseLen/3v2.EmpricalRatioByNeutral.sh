# Compute emprical ratio

#mutations.f550.gz
neu="../../bestFit/SCCT/empirical/yri/Length/"

ss=`seq 50 50 1000`
for l in ${ss}
do
    echo "zcat ../chooseLen/mutations.f${l}.gz | python ~/scct/ComputeRatioV1.1.py 2 3 4 ${neu}e.alpha.f${l}.txt |cut -f1,2,6 |gzip >ratio.f${l}.gz"
done

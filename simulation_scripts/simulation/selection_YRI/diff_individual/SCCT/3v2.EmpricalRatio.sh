# Compute emprical ratio

#mutations.f550.gz

n_a="../../../bestFit/SCCT/empirical/yri/Individual/"
ss=`seq 30 30 210`
for l in ${ss}
do
    echo "zcat mutations.id${l}.gz | python ~/scct/ComputeRatioV1.1.py 2 3 4 ${n_a}alpha.id${l}.txt |cut -f1,2,6 |gzip >ratio.id${l}.gz"
done

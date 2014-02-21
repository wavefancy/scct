# Compute emprical alpha_i, ratio of sum.

#mutations.f550.gz

ss=`seq 50 50 1000`
for l in ${ss}
do
    echo "zcat mutations.f${l}.gz | python  ~/scct/ComputeRatioExpectedV1.0.py 2 3 4 >e.alpha.f${l}.txt"
done

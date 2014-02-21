# Compute emprical alpha_i

#mutations.id210.gz

ss=`seq 30 30 210`
for l in ${ss}
do
    echo "zcat mutations.id${l}.gz | python ~/scct/ComputeRatioExpectedV1.0.py 2 3 4 >alpha.id${l}.txt"
done

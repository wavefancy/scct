# Compute emprical alpha_i

#mutations.id210.gz

ss=`cat ../para.sh`
for l in ${ss}
do
    echo "zcat mutations.id${l}.gz | python ~/scct/ComputeRatioExpectedV1.0.py 2 3 4 >alpha.id${l}.txt"
done

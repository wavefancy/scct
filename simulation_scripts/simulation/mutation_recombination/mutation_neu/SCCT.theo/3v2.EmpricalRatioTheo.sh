# Ratio normalized by theoretical alpha.

#mutations.f550.gz
theo="../../../theoretical/theoreticRatio_final_120.txt"

ss=`cat ../para.sh`
for l in ${ss}
do
    echo "zcat ../SCCT/mutations.id${l}.gz | python ~/scct/ComputeRatioV1.1.py 2 3 4 ${theo} |cut -f1,2,6 |gzip >ratio.id${l}.gz"
done

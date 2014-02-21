# Compute DAF
ss=`cat ../para.sh`
for s in ${ss}
do
    echo "paste <(zcat yri.${s}.daf.gz) <(zcat ceu.${s}.daf.gz | cut -f2) | awk -f ~/scct/daf.awk.sh | gzip >yri_ceu.daf.${s}.gz"
done

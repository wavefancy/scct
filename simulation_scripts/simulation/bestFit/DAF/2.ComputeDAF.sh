# Compute DAF

ss=`seq 30 30 210`
for s in ${ss}
do
    echo "paste <(zcat yri.id${s}.daf.gz) <(zcat ceu.id${s}.daf.gz | cut -f2) | awk -f daf.awk | gzip >yri_ceu.daf.id${s}.gz"
done

# Compute DAF

ss=`seq 500 100 2000`
for s in ${ss}
do
    echo "paste <(zcat yri.${s}.daf.gz) <(zcat ceu.${s}.daf.gz | cut -f2) | awk -f daf.awk.sh | gzip >yri_ceu.daf.${s}.gz"
done

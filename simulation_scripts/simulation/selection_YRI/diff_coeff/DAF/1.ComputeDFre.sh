# Compute derived allele freqency.

ss=`seq 500 100 2000`
for s in ${ss}
do
    echo "zcat ../r300_yri.${s}.gz | python ~/scct/MSAlleleFrequency.py | gzip >yri.${s}.daf.gz"
    echo "zcat ../r300_ceu.${s}.gz | python ~/scct/MSAlleleFrequency.py | gzip >ceu.${s}.daf.gz"
done

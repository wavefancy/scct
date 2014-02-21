# 1. Split according to populations.

#AFR_CEU.neu.n210.gz
ss=`cat para.sh`
for s in ${ss}
do
    echo "zcat AFR_CEU.sub.${s}.gz | java -jar -Xmx1G ~/scct/MSIndividualExtractor.V1.0.jar 1 120 | gzip >yri.${s}.gz"
    
    echo "zcat AFR_CEU.sub.${s}.gz | java -jar -Xmx1G ~/scct/MSIndividualExtractor.V1.0.jar 121 240 | gzip >ceu.${s}.gz"
done

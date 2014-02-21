
ss=`cat para.sh`
for s in ${ss}
do
    echo "java -Xmx100G -server -jar ~/scct/TheoreticalRatioV1.1.jar 45 ${s} > theoreticRatio_final_${s}.txt" 
done

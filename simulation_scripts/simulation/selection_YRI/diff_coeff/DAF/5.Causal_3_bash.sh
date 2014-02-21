# Compute power for each window length.

#inf="std.ratio.id*.gz"

#p=(0.316667 0.291666 0.272222 0.258333 0.25 0.241667 0.235714)
p="0.258333"
ss=(`cat ../para.sh`)
out="causal_3.txt"
echo ">${out}"
for((i=0;i<${#ss[@]};i++)){

    echo "echo ${ss[i]} >> ${out}"
    echo "zcat yri_ceu.daf.${ss[i]}.gz | node ~/scct/LocateCausalSite.js 25 ${p} 8 0.5000000000 1 4 3 >>${out}"
    echo "echo '' >> ${out}"

}

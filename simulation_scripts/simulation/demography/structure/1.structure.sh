# Simulate the structure model of YRI.

# Ne = 100000, u = 1.5 * 10^-8, r = 1.3 * 10^-8. L = 3m.
# theta = 4*Ne*u*L = 4*100000*1.5*10^-8*3*10^6 = 18000
# p = 4*Ne*r*L = 4 * 100000 * 1.3 * 10^-8 * 3 * 10^6 = 15600

# AF expansion:
# 200g/(4*100000) = 0.0005
# Ne_Africa_before expansion 24000 = 0.24

# EU expansion:
# 350/(4*100000) = 0.000875
# Ne_European_before expansion 7700 = 0.077

# EU out of africa: 3500/(4*100000) = 0.00875

# African ancestral expansion:
# 17000/(4*100000) = 0.0425
# Ne_before expansion: 12500 = 0.125

# 3pop substructure of YRI population.
# Time: 0g - 1000g: 0 - 0.0025
# Migration coefficient between sub-pop, 0 10 100.
# 0.24/4 = 0.06

ss="0 10 100"
for s in ${ss}
do
    #b=`echo "${s}*0.24" | bc -l`

    echo "java -jar -Xmx30G ~/scct/msms.jar 240 300 -t 18000 -r 15600 -N 100000 -I 5 30 30 30 30 120 ${s} -m 1 5 0 -m 5 1 0 -m 2 5 0 -m 5 2 0 -m 3 5 0 -m 5 3 0 -m 4 5 0 -m 5 4 0 -en 0 1 0.25 -en 0 2 0.25 -en 0 3 0.25 -en 0 4 0.25 -en 0.0005 1 0.06 -en 0.0005 2 0.06 -en 0.0005 3 0.06 -en 0.0005 4 0.06 -ej 0.0025 4 1 -ej 0.0025 3 1 -ej 0.0025 2 1 -en 0.0025 1 0.24 -en 0.000875 5 0.077 -ej 0.00875 5 1 -en 0.0425 1 0.125 -threads 10 | gzip > AFR_CEU.sub.${s}.gz"
done

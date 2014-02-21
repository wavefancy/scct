# copy sources.

dir="/media/Learn/ebook_Tutorial/java/userLib/projectBackup/SelectionByConditionalCoalescentTree/"
cp -r ${dir}/CountTwoGroupMutations ./
cp -r ${dir}/StandardizeFileGenerator ./
cp -r ${dir}/StandardizeFromFile ./

mkdir -p TheoreticalRatio
cp -r ${dir}/TheoreticalRatio/version1.1 ./TheoreticalRatio/

md="ComputeRatioFromMS"
mkdir -p ${md}
cp -r ${dir}/${md}/akka_version2.0_packagedWithExJars ./${md}/


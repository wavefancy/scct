#!/usr/bin/env python

'''
    Annotate region by gene.

    @author: wavefancy@gmail.com
    @verion: 1.0
    @since: 2013-04-26

'''
import sys

def help():
    sys.stderr.write('''
    Annotate region by gene.

    @author: wavefancy@gmail.com
    @verion: 1.0
    @since: 2013-04-26

    @usages:
    para1: gene_list_file
    para2: chr column index in gene_list_file
    para3: physical position start column index in gene_list_file
    para4: physical position end column index in gene_list_file
    para5: gene name column index in gene_list_file

    para6: chr column index of regions
    para7: physical position start column index of regions
    para8: physical position end column index of regions

    #note:
    1. Read window list from sys.stdin, output to sys.stdout after annotated.
    2. Column index start from 1.
    3. if dis != 0, indicates no gene in this window.Then the most near upstream/downstream(-) gene will be reported.
    \n''')
    sys.stderr.close()
    sys.exit(-1)


class Region(object):
    '''Represent a region'''

    def __init__(self, chr, pos_s, pos_e):
        self.chr = chr
        self.pos_start = pos_s
        self.pos_end = pos_e
        #left and right most near gene.
        self.left = [1000000000,'NA']
        self.right = [1000000000,'NA']
        self.Genes = [] #gene list in this region.

    def checkGene(self, left, right, name):
        if(right < self.pos_start):
            dis = self.pos_start - right
            if(dis < self.left[0]):
                self.left[0] = dis
                self.left[1] = name

        elif (left > self.pos_end):
            dis = left - self.pos_end
            if(dis < self.right[0]):
                self.right[0] = dis
                self.right[1] = name

        else:
            self.Genes.append(name)

    def __str__(self):
        pre = str(self.chr) + '\t' + str(self.pos_start) + '\t' + str(self.pos_end) + '\t'
        if(len(self.Genes) > 0):
            return pre +'0\t' + ','.join(self.Genes)
        else:
            if(self.left[0] < self.right[0]): #downstream.
                return pre + str(self.left[0]*-1)+'\t'+self.left[1]
            else:
                return pre + str(self.right[0]) + '\t' + self.right[1]

class Para(object):
    ''' Parameter settings '''
    gene_list_file = ''
    gene_chr_index = 0
    gene_pos_start_index = 0
    gene_pos_end_index = 0
    gene_name_index = 0

    re_chr_index = 0
    re_pos_start_index = 0
    re_pos_end_index = 0

if __name__ == '__main__':
    if(len(sys.argv) != 9):
        help()

    p = Para() #parameter settings.
    p.gene_list_file = sys.argv[1]
    p.gene_chr_index = int(sys.argv[2]) -1
    p.gene_pos_start_index = int(sys.argv[3]) -1
    p.gene_pos_end_index = int(sys.argv[4]) -1
    p.gene_name_index = int(sys.argv[5]) -1

    p.re_chr_index = int(sys.argv[6]) -1
    p.re_pos_start_index = int(sys.argv[7]) -1
    p.re_pos_end_index = int(sys.argv[8]) -1

    reginSet = {}
    for line in sys.stdin:
        line = line.strip()
        ss = line.split()
        if(len(ss) > 0):
            try:
                #try to parse physical position, if can't, skip this line.
                s = int(ss[p.re_pos_start_index])
                e = int(ss[p.re_pos_end_index])

                try:
                    x = reginSet[ss[p.re_chr_index]]
                    x.append( Region(ss[p.re_chr_index],s, e) )
                except KeyError:
                    #add a new chr.
                    reginSet.update({ss[p.re_chr_index]: []})

                    x = reginSet[ss[p.re_chr_index]]
                    x.append( Region(ss[p.re_chr_index],s,e) )

            except ValueError:
                pass

    #precess each gene.
    for line in open(p.gene_list_file):
        ss = line.strip().split()
        if(len(ss) > 0):
            try:
                p_s = int(ss[p.gene_pos_start_index])
                p_e = int(ss[p.gene_pos_end_index])

                #print(ss)
                
                try:
                  x = reginSet[ss[p.gene_chr_index]]
                  for i in x:
                      i.checkGene(p_s, p_e, ss[p.gene_name_index])
                except KeyError:
				  pass
                
            except ValueError:
                pass

    #output results.
    sys.stdout.write('#chr\tregion_start\tregion_end\tdis\tGene_list\n')
    #print(reginSet.keys())
    for k in sorted(reginSet.keys()):
        sys.stdout.write('\n'.join(map(str, reginSet[k])))
        sys.stdout.write('\n')

    sys.stdout.flush()
    sys.stdout.close()




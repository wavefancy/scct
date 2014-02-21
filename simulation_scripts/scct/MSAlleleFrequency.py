#!/usr/bin/env python

'''
    Compute allele frequency for ms format input data.
    
    @Author: wavefancy@gmail.com
    @Version: 1.0
    
    @Algorithms:
    1. Compute allele frequency for ms format input data.
        output allele frequency is derived allele frequncy.
        allele not '0' were treated as derived allele.
        
'''
import sys

def help():
    sys.stderr.write('''
    -------------------------------------
    MS_Allele Frequency Computer
    -------------------------------------
    
    @Author: wavefancy@gmail.com
    @Version: 1.0
    
    @Notes:
    1. Read ms format data from stdin and output to stdout.
    2. Input data can contain multiple ms datasets.
    3. Output derived allele frequncy, code not '0' were treated as derived allele.
    -------------------------------------
    \n''')
    sys.stderr.close()
    sys.exit(-1)
    
def DerivedFreComputer(data):
    '''Compute derived allele frequncy for ms data'''
    pos = data[0].split()[1:]
    snp = data[1:]
    
    for i in xrange(len(pos)):
        c = 0.0
        for x in snp:
            if x[i] != '0':
                c += 1.0
        
        sys.stdout.write('%s\t%.6f\n'%(pos[i], c/len(snp)))
    
if __name__ == '__main__':
    if(len(sys.argv) > 1):
        help()
    
    data = []
    add = False
    # read and process ms data.
    for line in sys.stdin:
        line = line.strip()
        if line:
            if add:
                data.append(line)
            else:
                if line.startswith('segsites'):
                    add = True
        else:
            add = False
            if data:
                DerivedFreComputer(data)
                data = []
    # process the last one
    if data:
        DerivedFreComputer(data)
    
    sys.stdout.flush()
    sys.stdout.close()
    sys.stderr.flush()
    sys.stderr.close()


#!/usr/bin/env python

'''
	Counting the number of significant sites in each window.
	
	@author: wavefancy@gmail.com
	@version: 1.0
	@since: 2013-3-19
	
'''
import sys

class Parameters:
	transform = True # whether transfrom to absolute value.
	bigger = True # bigger than the threshold. (>= or <=)

	threshold = 2.0 # >= , threshold for compare.
	pos_index = 1 # column index for physical position
	val_index = 5 # column index for values.
	win_size = 50 # window size
	step = 25 # step size.

	chr = 1# chromosome. 

class Window:
	start = 0 # window start
	end = 0 # window end
	chr = 1 # chromosome
	numSig = 0 # the number of significant sites.

	def __str__(self):
		return str(self.chr)+'\t'+str(self.start)+'\t'+str(self.end)+'\t'+str(self.numSig)
	
class NumOfSigInWindow:
	'''Counting the number of significant sites in each window'''
	pos = [] #store position
	values = [] # store values.
	windows = [] # store windows.
	
	def runApp(self):
		'''Parse input'''
		for line in sys.stdin:
			t = line.split()
			self.pos.append(t[Parameters.pos_index])
			if Parameters.transform:
				self.values.append(abs(float(t[Parameters.val_index])))
			else:
				self.values.append(float(t[Parameters.val_index]))

		#print(self.values)
		'''Check for each window'''
		for x in xrange(0,len(self.pos),Parameters.step):
			w = Window()
			w.start = x
			if (x + Parameters.win_size) > len(self.pos):
				w.end = len(self.pos)
			else:
				w.end = x + Parameters.win_size	
			w.chr = Parameters.chr

			'''Counting the number of significant sites'''
			for k in xrange(w.start,w.end): # do not including the last one.
				if Parameters.bigger:
					if self.values[k] >= Parameters.threshold:
						w.numSig = w.numSig +1 
				else:
					if self.values[k] <= Parameters.threshold:
						w.numSig = w.numSig +1 

			'''Chang window position to physical distance'''
			w.start = self.pos[w.start]
			w.end = self.pos[w.end-1]
			self.windows.append(w)

		'''output results'''
		sys.stdout.write('#chr\tPOS_start\tPOS_end\tnumSig\n')
		# for x in xrange(0, len(self.windows)):
		# 	sys.stdout.write(str(self.windows[x])+'\n')
		sys.stdout.write('\n'.join(map(str,self.windows)))
		sys.stdout.write('\n')
		sys.stdout.flush()

def help():
	sys.stderr.write('''
		Counting the number of significant sites in each window.
	
		@author: wavefancy@gmail.com
		@version: 1.0
		@since: 2013-3-19

		Usages:
		para1(0|1): whether transform to absolute value[1:yes].
		para2(0|1): 0: <= threshold, 1: >= threshold.
		para3(int): window size
		para4(int): step size
		para5(str): chr
		para6(float): threshold. (>=, or <=)
		para7(int): column index for physical position.
		para8(int): column index for values.
		[column index start from 1.]

		Input && output
		input from sys.stdin
		output to sys.stdout
		
		Note:
		Values were tranformed into absolute values.
	\n''');
	
	sys.stderr.flush()
	sys.exit()

if __name__=='__main__':
	if len(sys.argv) != 9:
		help()
	else:
		if(sys.argv[1] == '1'):
			Parameters.transform = True
		elif(sys.argv[1] == '0'):
			Parameters.transform = False
		else:
			sys.stderr.write('\n!!! [para1 should be 0 or 1.]\n')
			help()
			
		if(sys.argv[2] == '0'):
			Parameters.bigger = False
		elif(sys.argv[2] == '1'):
			Parameters.bigger = True
		else:
			sys.stderr.write('\n!!! [para2 should be 0 or 1.]\n')
			help()
		
		Parameters.win_size = int(sys.argv[3])
		Parameters.step = int(sys.argv[4])
		Parameters.chr = sys.argv[5]
		Parameters.threshold = float(sys.argv[6])
		Parameters.pos_index = int(sys.argv[7]) - 1 
		Parameters.val_index = int(sys.argv[8]) - 1 
		
		n = NumOfSigInWindow()
		n.runApp()

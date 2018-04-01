import os
import zipfile

SDK_NAMES = ['TwoCansAlpha1']
SDK_DIR = 'api'

def is_directory(path): return os.path.isdir(path.replace('/', os.sep))
def directory_list_files(path): return os.listdir(path.replace('/', os.sep))
def ensure_directory_exists(path):
	if not is_directory(path):
		sep_index = path.rfind('/')
		if sep_index == -1:
			subpath = path[:sep_index]
		else:
			subpath = '.'
		ensure_directory_exists(subpath)
		os.mkdir(path.replace('/', os.sep))
		
def gather_files(absolute_path, relative_path, output):
	for file in directory_list_files(absolute_path):
		full_abs_path = absolute_path + '/' + file
		full_rel_path = relative_path + '/' + file
		if is_directory(full_abs_path):
			gather_files(full_abs_path, full_rel_path, output)
		else:
			# TODO: skip banned files
			output.append((full_abs_path, full_rel_path))
			

def main():
	if not is_directory(SDK_DIR):
		print("Must run this from the TwoCans repository root.")
		return
	
	ensure_directory_exists('./gen-sdk')
	archive = zipfile.ZipFile(os.path.join('gen-sdk', 'TwoCansSDK.zip'), 'w', zipfile.ZIP_DEFLATED)
	for version in SDK_NAMES:
		files = []
		gather_files(SDK_DIR + '/' + version, 'TwoCansSDK/' + version, files)
		for abs_path, rel_path in files:
			archive.write(abs_path.replace('/', os.sep), rel_path)
	archive.close()
			
	
main()

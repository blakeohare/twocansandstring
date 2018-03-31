import os
import shutil
import sys

_BANNED_DIRECTORY_NAMES = [
	'/build',
	'/.gradle',
	'/.idea/libraries',
]

def pathify(p):
	return p.replace('/', os.sep)

def is_directory(p):
	return os.path.isdir(pathify(p))

def ensure_directory_created(p):
	if p == '.': return
	if not is_directory(p):
		parent = p[:p.rfind('/')]
		ensure_directory_created(parent)
		os.mkdir(pathify(p))

def copy_file(source, target):
	shutil.copy(pathify(source), pathify(target))

def list_files(path):
	return os.listdir(pathify(path))

def copy_files(source_dir, target_dir):
	copy_files_impl(source_dir, target_dir)

def copy_files_impl(source_dir, target_dir):
	for file in list_files(source_dir):
		full_source_path = source_dir + '/' + file
		full_target_path = target_dir + '/' + file
		if is_directory(full_source_path):
			
			for bf in _BANNED_DIRECTORY_NAMES:
				if full_source_path.endswith(bf):
					continue
					
			ensure_directory_created(full_target_path)
			copy_files_impl(full_source_path, full_target_path)
		else:
			copy_file(full_source_path, full_target_path)

def main(args):
	mobile_os = None
	if len(args) == 1:
		mobile_os = args[0]
		
	if not mobile_os in ('android', 'ios'):
		print "Usage:"
		print "  python build.py android"
		print "  python build.py ios"
		return
	
	if not (is_directory('ios') and is_directory('android') and is_directory('.git')):
		print "This script must be invoked from the root of the twocansandstring git repository"
		return
	
	print("Copying core " + mobile_os + " files...")
	root_dir = './' + mobile_os
	target_dir = './gen-' + mobile_os
	copy_files(root_dir, target_dir)
	print("Copying JavaScript files...")
	if mobile_os == 'android':
		copy_files('./mobilejs', './gen-android/app/src/main/assets')
	elif mobile_os == 'ios':
		copy_files('./mobilejs', './gen-ios/TwoCans/jsres')
	print("Done")

main(sys.argv[1:])

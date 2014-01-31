#!/bin/bash

echo 'Testing all examples, this will take some time...'

OK_MESSAGE="Pipeline completed successfully  :-)";
OUT="tests_output"
mkdir -p $OUT
echo "Logging progress to $OUT"

for script in `find . -name *pipeline`; do
	echo "* testing script $script..."
	script_out=$OUT/$(basename "$script").log
	./../../bin/run_pipeline $script &> $script_out
	if grep -qs "$OK_MESSAGE" $script_out; then
		echo '  -> passed'
	else
		echo "  -> failed (logs in $script_out)"
	fi
done
#!/usr/bin/env python3

import subprocess
import pathlib
import sys

INPUT_DIR = pathlib.Path(sys.argv[1]) if len(sys.argv) > 1 else pathlib.Path(".")
OUTPUT_DIR = INPUT_DIR / "converted"

OUTPUT_DIR.mkdir(exist_ok=True)

def run(cmd):
    print("Running:", " ".join(cmd))
    subprocess.run(cmd, check=True)

def convert_pdf(pdf_path):
    name = pdf_path.stem
    ps_file = OUTPUT_DIR / f"{name}.ps"
    out_pdf = OUTPUT_DIR / f"{name}.pdf"

    # PDF -> PS
    run([
        "pdftops",
        "-level2",
        str(pdf_path),
        str(ps_file)
    ])

    # PS -> compatible PDF
    run([
        "gs",
        "-sDEVICE=pdfwrite",
        "-dCompatibilityLevel=1.4",
        "-dNOPAUSE",
        "-dBATCH",
        "-dQUIET",
        f"-sOutputFile={out_pdf}",
        str(ps_file)
    ])

    ps_file.unlink()  # remove PS file
    print("Created:", out_pdf)

def main():
    pdfs = list(INPUT_DIR.glob("*.pdf"))

    if not pdfs:
        print("No PDFs found.")
        return

    for pdf in pdfs:
        convert_pdf(pdf)

if __name__ == "__main__":
    main()
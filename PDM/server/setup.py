from setuptools import setup, find_packages

install_requires = ['Django', 'djangorestframework']

setup(
    name='server',
    version='0.0.1',
    description="Server pt aplicatia PDM",
    author="Nichita Utiu",
    author_email="nikita.utiu@gmail.com",
    url="https://github.com/Presslabs/funky",
    install_requires=install_requires,
    tests_require=tests_require,
    packages=find_packages(exclude=['tests']),
    extras_require={
        'test': tests_require
    },
    classifiers=[
        'Environment :: Web Environment',
        'Framework :: Django',
        'Intended Audience :: Developers',
        'Operating System :: OS Independent',
        'Programming Language :: Python',
        'Programming Language :: Python :: 3',
        'Programming Language :: Python :: 3.5',
    ]
)

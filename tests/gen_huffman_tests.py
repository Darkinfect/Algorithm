import random

# Размеры тестов (растут экспоненциально и линейно до 2.5 млн)
sizes = [
    6, 10, 20, 50, 100, 500, 1000, 2500, 4000, 6500, 10000,
    20000, 30000, 50000, 100000, 200000, 250000, 400000, 600000,
    800000, 1000000, 1250000, 1500000, 1750000, 2000000, 2200000,
    2400000, 2450000, 2490000, 2500000
]

for idx, n in enumerate(sizes):
    if n < 10000:
        lo, hi = 1, 10**6
    elif n < 250000:
        lo, hi = 1, 10**4
    else:
        lo, hi = 1, 1000  # чтобы не было Out-of-Memory при запуске теста

    # Генерируем отсортированный список частот
    arr = sorted(random.randint(lo, hi) for _ in range(n))
    with open(f'huffman_test_{idx+1}.in', 'w') as f:
        f.write(f'{n}\n')
        f.write(' '.join(map(str, arr)) + '\n')

print("30 huffman_test_x.in файлов успешно сгенерировано!")

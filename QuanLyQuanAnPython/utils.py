def format_vnd(amount: float) -> str:
    """Định dạng số tiền theo VND"""
    return f"{amount:,.0f} ₫".replace(",", ".")

def parse_vnd(amount_str: str) -> float:
    """Chuyển chuỗi tiền thành số"""
    try:
        return float(amount_str.replace("₫", "").replace(".", "").replace(",", "").strip())
    except:
        return 0